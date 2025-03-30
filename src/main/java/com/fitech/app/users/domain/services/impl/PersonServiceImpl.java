package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.users.domain.model.PersonDto;
import com.fitech.app.users.domain.entities.Person;
import com.fitech.app.users.application.exception.DuplicatedUserException;
import com.fitech.app.users.application.exception.UserNotFoundException;
import com.fitech.app.users.infrastructure.repository.PersonRepository;
import com.fitech.app.users.domain.services.PersonService;
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
    public PersonDto save(PersonDto personDto) {
        Person person = MapperUtil.map(personDto, Person.class);
        if(personRepository.findByDocumentNumber(person.getDocumentNumber()).isPresent()){
            throw new DuplicatedUserException("Document number duplicated for " + personDto.getDocumentNumber());
        }
        person = personRepository.save(person);
        return MapperUtil.map(person, PersonDto.class);
    }
    @Override
    @Transactional
    public PersonDto update(Integer id, PersonDto personDto){
        Person personEntity = getPersonEntityById(id);
        // if you include document number different from actual, 
        // we need to validate that the new one should not exist
        if(personDto.hasDifferentDocumentNumber(personEntity.getDocumentNumber()) ){
            if(findByDocumentNumber(personDto.getDocumentNumber()) != null){
                throw new DuplicatedUserException("Document Number duplicated for " + personDto.getDocumentNumber());
            }
            personEntity.setDocumentNumber(personDto.getDocumentNumber());
        }
        personEntity.setPhoneNumber(personDto.getPhoneNumber());
        personEntity.setEmail(personDto.getEmail());
        personEntity.setFirstName(personDto.getFirstName());
        personEntity.setLastName(personDto.getLastName());
        personRepository.save(personEntity);
        return MapperUtil.map(personEntity, personDto.getClass());
    }
    

    @Override
    public PersonDto findByDocumentNumber(String documentNumber){
        Optional<Person> person = personRepository.findByDocumentNumber(documentNumber);
        return person.map(value -> MapperUtil.map(value, PersonDto.class)).orElse(null);
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
        if(optPerson.isPresent()){
            return optPerson.get();
        } else {
            throw new UserNotFoundException("Person not found with id: " + id);
        }
    }

    @Override
    public List<PersonDto> getAll(Pageable paging){
        Page<Person> personList = personRepository.findAll(paging);
        if(personList.isEmpty()){
            throw  new UserNotFoundException("Person does not exists");
        }
        return personList.map(person -> MapperUtil.map(person, PersonDto.class)).getContent();
    }

    public Person getOrCreatePersonEntity(PersonDto personDto){
        Person person = null;
        if (personDto.getId() == null) {
            // Create Person
            if(this.findByDocumentNumber(personDto.getDocumentNumber()) != null){
                throw new DuplicatedUserException("Document Number duplicated for " + personDto.getDocumentNumber());
            }
            // Create Person Entity
            person = new Person();
            person.setFirstName(personDto.getFirstName());
            person.setLastName(personDto.getLastName());
            person.setEmail(personDto.getEmail());
            person.setDocumentNumber(personDto.getDocumentNumber());
            person.setPhoneNumber(personDto.getPhoneNumber());
        } else {
            // otherwise get Person Entity from DB
            person = this.getPersonEntityById(personDto.getId());
        }
        return person;
    }
}
