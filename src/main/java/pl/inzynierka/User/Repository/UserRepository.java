package pl.inzynierka.User.Repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Query.PersonalInformationQuery;
import pl.inzynierka.User.Query.UpdateUserQuery;
import pl.inzynierka.User.Query.UsersListQuery;

import java.lang.String;
import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUsername(String username);

	@Query("SELECT new pl.inzynierka.User.Query.UsersListQuery(u.idUser, u.firstName, u.lastName, u.username, u.role, u.dateCreation) FROM users u")
	Page<UsersListQuery> findAllUsers(Pageable pageable);

	@Query("SELECT new pl.inzynierka.User.Query.UpdateUserQuery(u.firstName, u.lastName, u.role) FROM users u WHERE u.idUser = ?1")
	UpdateUserQuery findById(Long id);

	@Query("SELECT new pl.inzynierka.User.Query.PersonalInformationQuery(u.firstName, u.lastName) FROM users u WHERE u.username = ?1")
	PersonalInformationQuery findByUsernameAndReturnPersonalInformationQuery(String username);
}
