package com.fitech.app.users.domain.services;


import com.fitech.app.users.domain.model.PersonDto;
import com.fitech.app.users.domain.entities.Person;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    PersonDto save(PersonDto PersonDto);
    PersonDto update(Integer id, PersonDto dto);
    Optional<PersonDto> findByDocumentNumber(String documentNumber);
    PersonDto getById(Integer id);
    Person getPersonEntityById(Integer personDto);
    List<PersonDto> getAll(Pageable paging);
}
