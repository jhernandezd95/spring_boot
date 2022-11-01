package com.example.crud.modules.auth.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.common.http_errors.UnauthorizedException;
import com.example.crud.modules.auth.dto.RolesToUserDto;
import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.repositories.RoleRepository;
import com.example.crud.modules.auth.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#getAll()}
     */
    @Test
    void testGetAll() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualAll = userService.getAll();
        assertSame(userList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserService#getAll()}
     */
    @Test
    void testGetAll2() {
        when(userRepository.findAll()).thenThrow(new NotFoundException("https://example.org/example"));
        assertThrows(NotFoundException.class, () -> userService.getAll());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(UnauthorizedException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        User user = mock(User.class);
        when(user.getIsAccountNonExpired()).thenThrow(new UsernameNotFoundException("Msg"));
        when(user.getIsAccountNonLocked()).thenThrow(new UsernameNotFoundException("Msg"));
        when(user.getPassword()).thenThrow(new UsernameNotFoundException("Msg"));
        when(user.getUsername()).thenThrow(new UsernameNotFoundException("Msg"));
        when(user.getRoles()).thenThrow(new UsernameNotFoundException("Msg"));
        when(user.getIsEnable()).thenReturn(true);
        doNothing().when(user).setBirthDay((Date) any());
        doNothing().when(user).setCreatedAt((Date) any());
        doNothing().when(user).setDeletedAt((Date) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setIsAccountNonExpired((Boolean) any());
        doNothing().when(user).setIsAccountNonLocked((Boolean) any());
        doNothing().when(user).setIsEnable((Boolean) any());
        doNothing().when(user).setLastLogin((Date) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setRoles((Collection<Role>) any());
        doNothing().when(user).setUpdatedAt((Date) any());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByEmail((String) any());
        verify(user).getIsAccountNonExpired();
        verify(user).getIsEnable();
        verify(user).setBirthDay((Date) any());
        verify(user).setCreatedAt((Date) any());
        verify(user).setDeletedAt((Date) any());
        verify(user).setEmail((String) any());
        verify(user).setId((Long) any());
        verify(user).setIsAccountNonExpired((Boolean) any());
        verify(user).setIsAccountNonLocked((Boolean) any());
        verify(user).setIsEnable((Boolean) any());
        verify(user).setLastLogin((Date) any());
        verify(user).setPassword((String) any());
        verify(user).setRoles((Collection<Role>) any());
        verify(user).setUpdatedAt((Date) any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        User user = mock(User.class);
        when(user.getIsAccountNonExpired()).thenReturn(false);
        when(user.getIsAccountNonLocked()).thenReturn(true);
        when(user.getPassword()).thenReturn("iloveyou");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getRoles()).thenReturn(new ArrayList<>());
        when(user.getIsEnable()).thenReturn(true);
        doNothing().when(user).setBirthDay((Date) any());
        doNothing().when(user).setCreatedAt((Date) any());
        doNothing().when(user).setDeletedAt((Date) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setIsAccountNonExpired((Boolean) any());
        doNothing().when(user).setIsAccountNonLocked((Boolean) any());
        doNothing().when(user).setIsEnable((Boolean) any());
        doNothing().when(user).setLastLogin((Date) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setRoles((Collection<Role>) any());
        doNothing().when(user).setUpdatedAt((Date) any());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(UnauthorizedException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByEmail((String) any());
        verify(user).getIsAccountNonExpired();
        verify(user).getIsAccountNonLocked();
        verify(user).getIsEnable();
        verify(user).setBirthDay((Date) any());
        verify(user).setCreatedAt((Date) any());
        verify(user).setDeletedAt((Date) any());
        verify(user).setEmail((String) any());
        verify(user).setId((Long) any());
        verify(user).setIsAccountNonExpired((Boolean) any());
        verify(user).setIsAccountNonLocked((Boolean) any());
        verify(user).setIsEnable((Boolean) any());
        verify(user).setLastLogin((Date) any());
        verify(user).setPassword((String) any());
        verify(user).setRoles((Collection<Role>) any());
        verify(user).setUpdatedAt((Date) any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername4() throws UsernameNotFoundException {
        User user = mock(User.class);
        when(user.getIsAccountNonExpired()).thenReturn(true);
        when(user.getIsAccountNonLocked()).thenReturn(true);
        when(user.getPassword()).thenReturn("iloveyou");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getRoles()).thenReturn(new ArrayList<>());
        when(user.getIsEnable()).thenReturn(false);
        doNothing().when(user).setBirthDay((Date) any());
        doNothing().when(user).setCreatedAt((Date) any());
        doNothing().when(user).setDeletedAt((Date) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setIsAccountNonExpired((Boolean) any());
        doNothing().when(user).setIsAccountNonLocked((Boolean) any());
        doNothing().when(user).setIsEnable((Boolean) any());
        doNothing().when(user).setLastLogin((Date) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setRoles((Collection<Role>) any());
        doNothing().when(user).setUpdatedAt((Date) any());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(UnauthorizedException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByEmail((String) any());
        verify(user).getIsEnable();
        verify(user).setBirthDay((Date) any());
        verify(user).setCreatedAt((Date) any());
        verify(user).setDeletedAt((Date) any());
        verify(user).setEmail((String) any());
        verify(user).setId((Long) any());
        verify(user).setIsAccountNonExpired((Boolean) any());
        verify(user).setIsAccountNonLocked((Boolean) any());
        verify(user).setIsEnable((Boolean) any());
        verify(user).setLastLogin((Date) any());
        verify(user).setPassword((String) any());
        verify(user).setRoles((Collection<Role>) any());
        verify(user).setUpdatedAt((Date) any());
    }

    /**
     * Method under test: {@link UserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername5() throws UsernameNotFoundException {
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        User user = mock(User.class);
        when(user.getIsAccountNonExpired()).thenReturn(true);
        when(user.getIsAccountNonLocked()).thenReturn(true);
        when(user.getPassword()).thenReturn("iloveyou");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getRoles()).thenReturn(new ArrayList<>());
        when(user.getIsEnable()).thenReturn(true);
        doNothing().when(user).setBirthDay((Date) any());
        doNothing().when(user).setCreatedAt((Date) any());
        doNothing().when(user).setDeletedAt((Date) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setIsAccountNonExpired((Boolean) any());
        doNothing().when(user).setIsAccountNonLocked((Boolean) any());
        doNothing().when(user).setIsEnable((Boolean) any());
        doNothing().when(user).setLastLogin((Date) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setRoles((Collection<Role>) any());
        doNothing().when(user).setUpdatedAt((Date) any());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        assertThrows(NotFoundException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByEmail((String) any());
        verify(user).setBirthDay((Date) any());
        verify(user).setCreatedAt((Date) any());
        verify(user).setDeletedAt((Date) any());
        verify(user).setEmail((String) any());
        verify(user).setId((Long) any());
        verify(user).setIsAccountNonExpired((Boolean) any());
        verify(user).setIsAccountNonLocked((Boolean) any());
        verify(user).setIsEnable((Boolean) any());
        verify(user).setLastLogin((Date) any());
        verify(user).setPassword((String) any());
        verify(user).setRoles((Collection<Role>) any());
        verify(user).setUpdatedAt((Date) any());
    }

    /**
     * Method under test: {@link UserService#getById(Long)}
     */
    @Test
    void testGetById() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(user, userService.getById(123L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#getById(Long)}
     */
    @Test
    void testGetById2() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getById(123L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#getById(Long)}
     */
    @Test
    void testGetById3() {
        when(userRepository.findById((Long) any())).thenThrow(new NotFoundException("https://example.org/example"));
        assertThrows(NotFoundException.class, () -> userService.getById(123L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#expireAccounts(int)}
     */
    @Test
    void testExpireAccounts() {
        when(userRepository.findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any()))
                .thenReturn(new ArrayList<>());
        when(userRepository.saveAll((Iterable<User>) any())).thenReturn(new ArrayList<>());
        userService.expireAccounts(3);
        verify(userRepository).findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any());
        verify(userRepository).saveAll((Iterable<User>) any());
        assertTrue(userService.getAll().isEmpty());
    }

    /**
     * Method under test: {@link UserService#expireAccounts(int)}
     */
    @Test
    void testExpireAccounts2() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any()))
                .thenReturn(userList);
        when(userRepository.saveAll((Iterable<User>) any())).thenReturn(new ArrayList<>());
        userService.expireAccounts(3);
        verify(userRepository).findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any());
        verify(userRepository).saveAll((Iterable<User>) any());
    }

    /**
     * Method under test: {@link UserService#expireAccounts(int)}
     */
    @Test
    void testExpireAccounts3() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));

        User user1 = new User();
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirthDay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setDeletedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setIsAccountNonExpired(true);
        user1.setIsAccountNonLocked(true);
        user1.setIsEnable(true);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setPassword("iloveyou");
        user1.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setUpdatedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user);
        when(userRepository.findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any()))
                .thenReturn(userList);
        when(userRepository.saveAll((Iterable<User>) any())).thenReturn(new ArrayList<>());
        userService.expireAccounts(3);
        verify(userRepository).findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any());
        verify(userRepository).saveAll((Iterable<User>) any());
    }

    /**
     * Method under test: {@link UserService#expireAccounts(int)}
     */
    @Test
    void testExpireAccounts4() {
        when(userRepository.findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any()))
                .thenThrow(new UsernameNotFoundException("Msg"));
        when(userRepository.saveAll((Iterable<User>) any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> userService.expireAccounts(3));
        verify(userRepository).findAllByLastLoginBeforeAndIsAccountNonExpired((Date) any(), (Boolean) any());
    }

    /**
     * Method under test: {@link UserService#remove(Long)}
     */
    @Test
    void testRemove() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        userService.remove(123L);
        verify(userRepository).findById((Long) any());
        verify(userRepository).delete((User) any());
        assertTrue(userService.getAll().isEmpty());
    }

    /**
     * Method under test: {@link UserService#remove(Long)}
     */
    @Test
    void testRemove2() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        doThrow(new NotFoundException("https://example.org/example")).when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(NotFoundException.class, () -> userService.remove(123L));
        verify(userRepository).findById((Long) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserService#remove(Long)}
     */
    @Test
    void testRemove3() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.remove(123L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#changeLockedValue(Long)}
     */
    @Test
    void testChangeLockedValue() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirthDay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setDeletedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setIsAccountNonExpired(true);
        user1.setIsAccountNonLocked(true);
        user1.setIsEnable(true);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setPassword("iloveyou");
        user1.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setUpdatedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        User actualChangeLockedValueResult = userService.changeLockedValue(123L);
        assertSame(user, actualChangeLockedValueResult);
        assertFalse(actualChangeLockedValueResult.getIsAccountNonLocked());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#changeLockedValue(Long)}
     */
    @Test
    void testChangeLockedValue2() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenThrow(new NotFoundException("https://example.org/example"));
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(NotFoundException.class, () -> userService.changeLockedValue(123L));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#changeExpiredValue(Long)}
     */
    @Test
    void testChangeExpiredValue() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirthDay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setDeletedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setIsAccountNonExpired(true);
        user1.setIsAccountNonLocked(true);
        user1.setIsEnable(true);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setPassword("iloveyou");
        user1.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setUpdatedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        User actualChangeExpiredValueResult = userService.changeExpiredValue(123L);
        assertSame(user, actualChangeExpiredValueResult);
        assertFalse(actualChangeExpiredValueResult.getIsAccountNonExpired());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#changeExpiredValue(Long)}
     */
    @Test
    void testChangeExpiredValue2() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenThrow(new NotFoundException("https://example.org/example"));
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(NotFoundException.class, () -> userService.changeExpiredValue(123L));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#addRoleToUser(Long, RolesToUserDto)}
     */
    @Test
    void testAddRoleToUser() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(roleRepository.findByIdIn((Long[]) any())).thenReturn(new ArrayList<>());
        doNothing().when(roleRepository).deleteAll((Iterable<Role>) any());

        RolesToUserDto rolesToUserDto = new RolesToUserDto();
        rolesToUserDto.setRolesId(new Long[]{123L});
        assertThrows(NotFoundException.class, () -> userService.addRoleToUser(123L, rolesToUserDto));
        verify(userRepository).findById((Long) any());
        verify(roleRepository).findByIdIn((Long[]) any());
        verify(roleRepository).deleteAll((Iterable<Role>) any());
    }

    /**
     * Method under test: {@link UserService#addRoleToUser(Long, RolesToUserDto)}
     */
    @Test
    void testAddRoleToUser2() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(roleRepository.findByIdIn((Long[]) any()))
                .thenThrow(new UsernameNotFoundException("Not all provided roles exist"));
        doThrow(new UsernameNotFoundException("Not all provided roles exist")).when(roleRepository)
                .deleteAll((Iterable<Role>) any());

        RolesToUserDto rolesToUserDto = new RolesToUserDto();
        rolesToUserDto.setRolesId(new Long[]{123L});
        assertThrows(UsernameNotFoundException.class, () -> userService.addRoleToUser(123L, rolesToUserDto));
        verify(userRepository).findById((Long) any());
        verify(roleRepository).deleteAll((Iterable<Role>) any());
    }

    /**
     * Method under test: {@link UserService#addRoleToUser(Long, RolesToUserDto)}
     */
    @Test
    void testAddRoleToUser3() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(roleRepository.findByIdIn((Long[]) any())).thenReturn(new ArrayList<>());
        doNothing().when(roleRepository).deleteAll((Iterable<Role>) any());

        RolesToUserDto rolesToUserDto = new RolesToUserDto();
        rolesToUserDto.setRolesId(new Long[]{123L});
        assertThrows(NotFoundException.class, () -> userService.addRoleToUser(123L, rolesToUserDto));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#addRoleToUser(Long, RolesToUserDto)}
     */
    @Test
    void testAddRoleToUser4() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDeletedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsEnable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLogin(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirthDay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setDeletedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setIsAccountNonExpired(true);
        user1.setIsAccountNonLocked(true);
        user1.setIsEnable(true);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLogin(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setPassword("iloveyou");
        user1.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setUpdatedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setBirthDay(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setCreatedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setDeletedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setEmail("jane.doe@example.org");
        user2.setId(123L);
        user2.setIsAccountNonExpired(true);
        user2.setIsAccountNonLocked(true);
        user2.setIsEnable(true);
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLogin(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setPassword("iloveyou");
        user2.setRoles(new ArrayList<>());
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setUpdatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));

        Role role = new Role();
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        role.setCreatedBy(user2);
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setDeletedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        role.setDescription("The characteristics of someone or something");
        role.setId(123L);
        role.setName("Not all provided roles exist");
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setUpdatedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        role.setUsers(new ArrayList<>());

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role);
        when(roleRepository.findByIdIn((Long[]) any())).thenReturn(roleList);
        doNothing().when(roleRepository).deleteAll((Iterable<Role>) any());

        RolesToUserDto rolesToUserDto = new RolesToUserDto();
        rolesToUserDto.setRolesId(new Long[]{123L});
        userService.addRoleToUser(123L, rolesToUserDto);
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(roleRepository).findByIdIn((Long[]) any());
        verify(roleRepository).deleteAll((Iterable<Role>) any());
    }
}

