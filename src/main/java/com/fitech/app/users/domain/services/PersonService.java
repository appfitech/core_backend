package com.fitech.app.users.domain.services;


import com.fitech.app.users.domain.model.PersonDto;
import com.fitech.app.users.domain.entities.Person;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    PersonDto save(PersonDto PersonDto);
    PersonDto update(Integer id, PersonDto dto);
    PersonDto findByDocumentNumber(String documentNumber);
    PersonDto getById(Integer id);
    Person getPersonEntityById(Integer personDto);
    Person getOrCreatePersonEntity(PersonDto personDto);
    List<PersonDto> getAll(Pageable paging);
}
