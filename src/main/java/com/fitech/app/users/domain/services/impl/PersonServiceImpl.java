package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.domain.model.PersonDto;
import com.fitech.app.users.domain.entities.Person;
import com.fitech.app.users.application.exception.DuplicatedUserException;
import com.fitech.app.users.application.exception.UserNotFoundException;
import com.fitech.app.users.infrastructure.repository.PersonRepository;
import com.fitech.app.users.domain.services.PersonService;
import com.fitech.app.users.application.wrappers.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public Person save(PersonDto personDto) {
        validatePersonCreation(personDto);
        Person person = createPersonEntity(personDto);
        return personRepository.save(person);
    }

    private void validatePersonCreation(PersonDto personDto) {
        if (personRepository.findByDocumentNumber(personDto.getDocumentNumber()).isPresent()) {
            throw new DuplicatedUserException("Document number duplicated for " + personDto.getDocumentNumber());
        }
    }

    private Person createPersonEntity(PersonDto personDto) {
        return MapperUtil.map(personDto, Person.class);
    }

    @Override
    @Transactional
    public PersonDto update(Integer id, PersonDto personDto) {
        // Get the existing person entity
        Person personEntity = getPersonEntityById(id);
        
        // Validate document number if it's being changed
        if(personDto.hasDifferentDocumentNumber(personEntity.getDocumentNumber())) {
            if(findByDocumentNumber(personDto.getDocumentNumber()).isPresent()) {
                throw new DuplicatedUserException("Document Number duplicated for " + personDto.getDocumentNumber());
            }
        }
        
        // Map DTO to entity while preserving the ID
        Person updatedPerson = MapperUtil.map(personDto, Person.class);
        updatedPerson.setId(id);  // Ensure ID is preserved
        
        // Save the updated entity
        personEntity = personRepository.save(updatedPerson);
        
        // Map back to DTO and return
        return MapperUtil.map(personEntity, PersonDto.class);
    }
    

    @Override
    public Optional<PersonDto> findByDocumentNumber(String documentNumber){
        Optional<Person> person = personRepository.findByDocumentNumber(documentNumber);
        return person.map(value -> MapperUtil.map(value, PersonDto.class));
    }

    @Override
    public PersonDto getById(Integer id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty()){
            throw new UserNotFoundException("Person Id does not exists: " + id);
        }
        return MapperUtil.map(person.get(), PersonDto.class);
    }

    @Override
    public Person getPersonEntityById(Integer id) {
        Optional<Person> optPerson = personRepository.findById(id);
        return optPerson.orElseThrow(() -> new UserNotFoundException("Person not found with id: " + id));
    }

    @Override
    public ResultPage<PersonDto> getAll(Pageable paging){
        Page<Person> personList = personRepository.findAll(paging);
        if(personList.isEmpty()){
            throw new UserNotFoundException("Person does not exists");
        }
        return PaginationUtil.prepareResultWrapper(personList, PersonDto.class);
    }
}
