package com.fluxchat.fluxchat.services;

import com.fluxchat.fluxchat.controllers.dtos.UserChatParticipantDto;
import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.enteties.enums.Role;
import com.fluxchat.fluxchat.exceptions.UserNotFoundException;
import com.fluxchat.fluxchat.repositories.UserRepository;
import com.fluxchat.fluxchat.services.interfaces.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder ) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getAllByIds(Collection<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public User findById(Long id) {
        return getUserById(id);
    }

    @Override
    public User create(User user) {
        if( repository.findByEmail(user.getEmail()).isPresent() || repository.findByPhone(user.getPhone()).isPresent() ) {
            throw new RuntimeException( "Користувач з таким емайлом або номером телефону вже існує" );
        }
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(Role.ADMIN); //TODO
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = getUserById(id);
        repository.delete(user);
    }

    @Override
    public User update(Long id, User updateUser) {
        User user = getUserById(id);

        setValue(updateUser :: getFirstName, user ::setFirstName );

        setValue(updateUser :: getLastName, user :: setLastName);

        setValue(updateUser :: getPhone, user :: setPhone);

        setValue(updateUser :: getEmail, user :: setEmail);

        if( updateUser.getPassword() != null && !Strings.isEmpty(updateUser.getPassword()) ) {
            user.setPassword(bCryptPasswordEncoder.encode( updateUser.getPassword() ) );
        }
        return repository.save(user);
    }

    private void setValue(Supplier<String> getValue, Consumer<String> setValue) {
        if( getValue.get() != null && !Strings.isEmpty(getValue.get()) ) {
            setValue.accept(getValue.get());
        }
    }

    @Override
    public Page<User> getAllPages(int page, int pageSize, String sort, Long mafId) {
        Pageable pageable = PageRequest.of(page, pageSize, getSortObject(sort));

        return repository.findAll(pageable);
    }

    @Override
    public List<UserChatParticipantDto> findAllUsersOutOfChat(Long chatId) {
        return repository.findAllOutOfChatRoom(chatId).stream().map(UserChatParticipantDto::from).toList();
    }

    private User getUserById( Long id ) {
        Optional<User> user = repository.findById( id );
        if( user.isEmpty()) {
            throw new UserNotFoundException("Користувача з id : " + id + " не знайдено");
        }
        return user.get();
    }

    private Sort getSortObject(String sort ) {
        String[] sortParams = sort.split(",");
        String sortBy = sortParams[0];
        String sortOrder = sortParams.length > 1 ? sortParams[1] : "ASC";
        return Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
    }
}
