create table employees
(
    id          bigint auto_increment
        primary key,
    first_name  varchar(50) not null,
    last_name   varchar(50) not null,
    middle_name varchar(50) not null,
    constraint UKmaduxc3mxbmp0wyauo6hfxi20
        unique (first_name, last_name, middle_name)
);

create table tasks
(
    id              bigint auto_increment
        primary key,
    completed       bit          not null,
    completion_date date         not null,
    end_date        date         not null,
    name            varchar(255) not null,
    start_date      date         not null,
    employee_id     bigint       not null,
    constraint FKjc3xiile6e5jbtmywxw5vfm7f
        foreign key (employee_id) references employees (id),
    constraint date_check
        check ((start_date <= end_date) and (completion_date between start_date and end_date))
);

