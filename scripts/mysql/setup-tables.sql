DROP TABLE citations;
CREATE TABLE citations (
    id integer DEFAULT 0 NOT NULL,
    citation_number text NULL,
    citation_date date NULL,
    first_name text NULL,
    last_name text NULL,
    date_of_birth date NULL,
    defendant_address text NULL,
    defendant_city text NULL,
    defendant_state text NULL,
    drivers_license_number text,
    court_date date NULL,
    court_location text NULL,
    court_address text NULL,
    court_id integer NULL
);

DROP TABLE courts;
CREATE TABLE courts (
    id integer NOT NULL,
    latitude double precision,
    longitude double precision,
    municipality text,
    address text,
    city text,
    state text,
    zip_code text
);

DROP TABLE opportunities;
CREATE TABLE opportunities (
    id integer NOT NULL,
    sponsor_id integer NOT NULL,
    name text NOT NULL,
    short_description text NOT NULL,
    full_description text,
    court_id integer NOT NULL
);

DROP TABLE opportunity_need_pairings;
CREATE TABLE opportunity_need_pairings (
    opportunity_need_id integer,
    violation_id integer,
    status text,
    id integer
);

DROP TABLE opportunity_needs;
CREATE TABLE opportunity_needs (
    id integer NOT NULL,
    opportunity_id integer,
    start_time DATETIME,
    end_time DATETIME,
    violation_fine_limit numeric,
    desired_count integer,
    description text
);

DROP TABLE sponsor_login;
CREATE TABLE sponsor_login (
    id integer NOT NULL,
    userid text,
    pwd text
);

DROP TABLE sponsors;
CREATE TABLE sponsors (
    id integer NOT NULL,
    name text,
    short_description text,
    contact_email text,
    contact_phonenumber text
);

DROP TABLE violations;
CREATE TABLE violations (
    id integer DEFAULT 0 NOT NULL,
    citation_number text,
    violation_number text,
    violation_description text,
    warrant_status TINYINT(1) DEFAULT 0,
    warrant_number text,
    status text,
    status_date TIMESTAMP,
    fine_amount numeric(15,2),
    court_cost numeric(15,2)
);
