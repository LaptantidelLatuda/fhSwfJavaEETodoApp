CREATE DATABASE TodoApp;

USE TodoApp;

CREATE TABLE `Todos` (
	`idTodo` INT PRIMARY KEY AUTO_INCREMENT,
	`titel` VARCHAR(50) NOT NULL,
	`description` VARCHAR(1000),
	`dueDate` DATE,
	`isDone` BOOLEAN
);

INSERT INTO `Todos`
(titel, description, dueDate, isDone)
VALUES 
('Müll rausbringen', 'Riecht schon.', '2021-10-01', FALSE),
('Hausarbeit schreiben', 'Ist ja immerhin die letzte.', '2021-10-02', FALSE),
('Hausarbeit wirklich schreiben', 'Wenn ich nicht so viel in meinem Erststudium prokrastiniert hätte, hätte ich ja jetzt Urlaub.', '2021-09-05', FALSE),
('Rasen mähen', '', '2021-10-04', FALSE),
('Sich mit interessanten Technologien beschäftigen', 'Die Preview von .NET 6 wirkt sehr vielversprechend. Da kann man tatsächlich ohne eine Krücke wie Javascript native Aplikationen Crossplattform schreiben. Blazor ist schon richtig toll... und dieses Webassembly erst. Ich denke ich werde meinem Arbeitgeber vorschlagen unsere Software Suite auf Android zu portieren WEIL ES GEHT!!!!.', '2021-10-03', FALSE),
('Weltherrschaft an sich reißen', 'Könnte aber stressig werden, mal schauen.', '2021-10-10', FALSE);