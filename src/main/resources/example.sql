-- create schema project
create table project.employees(first_name varchar,
                        last_name varchar,
                        age int,
                        skill varchar);

alter table project.employees add column id serial;
alter table project.employees rename column id to student_id;

create table project.skill(id serial primary key, skill varchar);

alter table project.employees
    add constraint fk_skill_id foreign key (skill) references project.skill (id);

select * from project.employees;

select first_name, last_name, skill.skill from project.employees inner join project.skill on employees.skill = skill.id;
select first_name, last_name, s.skill from project.employees left join project.skill s on s.id = employees.skill;
select first_name, last_name, skill.skill from project.employees right join project.skill on employees.skill = skill.id;
select first_name, last_name, skill.skill from project.employees full outer join project.skill on employees.skill = skill.id;


select * from project.employees
where project.employees.skill = 3;

create type skill_type as enum (
    'LOW',
    'MID',
    'HIGH'
    )



