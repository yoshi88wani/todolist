CREATE TABLE tasks (
    id INT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    completed BOOLEAN,
    dueDate DATE
);

INSERT INTO tasks (id, title, description, completed, dueDate) VALUES
(1, 'Test Task 1', 'Description for task 1', false, '2023-08-30'),
(2, 'Test Task 2', 'Description for task 2', true, '2023-08-31');
