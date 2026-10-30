package com.github.tabreubr.musiclass.infra.config;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Instrument;
import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.enums.UserRole;
import com.github.tabreubr.musiclass.repositories.InstructorRepository;
import com.github.tabreubr.musiclass.repositories.InstrumentRepository;
import com.github.tabreubr.musiclass.repositories.MethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Dados de exemplo para desenvolvimento local.
 * Só roda com o perfil "dev": em qualquer outro ambiente, nenhum usuário com
 * senha conhecida é criado.
 */
@Component
@Profile("dev")
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final InstructorRepository instructorRepository;
    private final InstrumentRepository instrumentRepository;
    private final MethodRepository methodRepository;
    private final PasswordEncoder passwordEncoder;
    private final String instructorPassword;

    public DataInitializer(InstructorRepository instructorRepository,
                           InstrumentRepository instrumentRepository,
                           MethodRepository methodRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${app.seed.instructor-password}") String instructorPassword) {
        this.instructorRepository = instructorRepository;
        this.instrumentRepository = instrumentRepository;
        this.methodRepository = methodRepository;
        this.passwordEncoder = passwordEncoder;
        this.instructorPassword = instructorPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedInstructor("Thiago Abreu", "thiago@musiclass.com");
        seedInstructor("Guilherme Abreu", "gui@musiclass.com");
        seedInstruments();
        seedMethods();
    }

    private void seedInstructor(String name, String email) {
        if (instructorRepository.findByEmail(email).isEmpty()) {
            Instructor instructor = new Instructor();
            instructor.setName(name);
            instructor.setEmail(email);
            instructor.setPassword(passwordEncoder.encode(instructorPassword));
            instructor.setRole(UserRole.INSTRUCTOR);
            instructorRepository.save(instructor);
            log.info("Dev seed: instructor created ({})", email);
        }
    }

    private void seedInstruments() {
        for (String name : new String[]{"Violin", "Trompa", "Tuba"}) {
            if (instrumentRepository.findByName(name).isEmpty()) {
                Instrument instrument = new Instrument();
                instrument.setName(name);
                instrumentRepository.save(instrument);
            }
        }
    }

    private void seedMethods() {
        for (String name : new String[]{"Bona", "Schimoll", "Suzuki"}) {
            if (methodRepository.findByName(name).isEmpty()) {
                Method method = new Method();
                method.setName(name);
                methodRepository.save(method);
            }
        }
    }
}
