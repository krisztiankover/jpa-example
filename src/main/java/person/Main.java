package person;

import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static Faker faker = new Faker(new Locale("hu"));

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        for (int i = 0; i < 1000; i++) {
            Person person = randomPerson();
            em.persist(person);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static Person randomPerson() {
        Date date = faker.date().birthday();
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Person person = Person.builder()
                .name(faker.name().name())
                .dob(ld)
                .gender(faker.options().option(Person.Gender.values()))
                .address(Address.builder()
                        .country(faker.address().country())
                        .state(faker.address().state())
                        .city(faker.address().city())
                        .streetAddress(faker.address().streetAddress())
                        .zip(faker.address().zipCode())
                        .build())
                .email(faker.internet().emailAddress())
                .profession(faker.company().profession())
                .build();

        return person;

    }
}
