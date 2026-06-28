DROP TABLE IF EXISTS exercises;
DROP TABLE IF EXISTS workouts;
DROP TABLE IF EXISTS gym_membership;

CREATE TABLE gym_membership (
                                id SERIAL PRIMARY KEY,
                                next_payment_date DATE NOT NULL,
                                price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE workouts (
                          id SERIAL PRIMARY KEY,
                          visit_date DATE NOT NULL,
                          notes TEXT,
                          with_trainer BOOLEAN DEFAULT FALSE,
                          trainer_paid BOOLEAN DEFAULT FALSE
);

CREATE TABLE exercises (
                           id SERIAL PRIMARY KEY,
                           workout_id INT REFERENCES workouts(id) ON DELETE CASCADE,
                           name VARCHAR(255) NOT NULL,
                           weight NUMERIC(5, 2),
                           sets_reps VARCHAR(50) NOT NULL
);
