DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL, 
    completed BOOLEAN DEFAULT FALSE,
    dueDate DATE NULL 
);

INSERT INTO tasks (title, description, completed, dueDate) VALUES
('Test Task 1', 'Description for task 1', false, '2023-08-30'),
('Test Task 2', 'Description for task 2', true, '2023-08-31');
