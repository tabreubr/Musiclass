package com.github.tabreubr.musiclass.infra.config;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Instrument;
import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.enums.UserRole;
import com.github.tabreubr.musiclass.repositories.InstructorRepository;
import com.github.tabreubr.musiclass.repositories.InstrumentRepository;
import com.github.tabreubr.musiclass.repositories.MethodRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final InstructorRepository instructorRepository;
    private final InstrumentRepository instrumentRepository;
    private final MethodRepository methodRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(InstructorRepository instructorRepository,
                           InstrumentRepository instrumentRepository,
                           MethodRepository methodRepository,
                           PasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.instrumentRepository = instrumentRepository;
        this.methodRepository = methodRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedInstructor();
        seedInstruments();
        seedMethods();
    }

    private void seedInstructor() {
        if (instructorRepository.findByEmail("thiago@musiclass.com").isEmpty()) {
            Instructor instructor = new Instructor();
            instructor.setName("Thiago Abreu");
            instructor.setEmail("thiago@musiclass.com");
            instructor.setPassword(passwordEncoder.encode("123456"));
            instructor.setRole(UserRole.INSTRUCTOR);
            instructorRepository.save(instructor);
            System.out.println("✅ Instrutor padrão criado: thiago@musiclass.com / 123456");
        }
        if (instructorRepository.findByEmail("gui@musiclass.com").isEmpty()) {
            Instructor instructor2 = new Instructor();
            instructor2.setName("Guilherme Abreu");
            instructor2.setEmail("gui@musiclass.com");
            instructor2.setPassword(passwordEncoder.encode("123456"));
            instructor2.setRole(UserRole.INSTRUCTOR);
            instructorRepository.save(instructor2);
            System.out.println("✅ Instrutor 2 criado: gui@musiclass.com / 123456");
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