package org.gym.dao.impl;

import org.gym.dao.ExerciseDao;
import org.gym.model.Exercise;

import java.sql.SQLException;
import java.util.List;

public class ExerciseDaoImpl implements ExerciseDao {
    String sql_save = "INSERT INTO exercises (workout_id, name, weight, sets_reps) VALUES (?, ?, ?, ?)";

    @Override
    public void save(Exercise exercise) throws SQLException {

    }

    @Override
    public List<Exercise> getExercisesByWorkoutId(int workoutId) throws SQLException {
        return List.of();
    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
