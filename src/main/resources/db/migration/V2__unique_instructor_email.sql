-- V2: e-mail de instrutor obrigatório, normalizado e único.
-- Sem isso, dois instrutores podiam ter o mesmo e-mail, e o login de um deles
-- quebrava (consulta por e-mail retornando duas linhas).

UPDATE instructor SET email = LOWER(TRIM(email)) WHERE email IS NOT NULL;

ALTER TABLE instructor ALTER COLUMN email SET NOT NULL;

ALTER TABLE instructor ADD CONSTRAINT uk_instructor_email UNIQUE (email);
