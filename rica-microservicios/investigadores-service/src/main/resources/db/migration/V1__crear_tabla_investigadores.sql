CREATE TABLE investigadores (
    id BIGSERIAL PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    correo_institucional VARCHAR(150) NOT NULL UNIQUE,
    grupo_investigacion VARCHAR(120) NOT NULL
);
