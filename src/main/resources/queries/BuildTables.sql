CREATE TABLE sites (
   id INT NOT NULL,
   name VARCHAR(50) NOT NULL,
   address VARCHAR(50) NOT NULL,
   city VARCHAR(20) NOT NULL,
   state VARCHAR(20) NOT NULL,
   zipcode VARCHAR(20) NOT NULL
);

CREATE TABLE site_uses (
   id INT NOT NULL,
   description VARCHAR(50) NOT NULL,
   site_id INT NOT NULL,
   size_sqft BIGINT NOT NULL,
   use_type_id INT NOT NULL
);

CREATE TABLE use_types (
   id INT NOT NULL,
   name VARCHAR(50) NOT NULL
);