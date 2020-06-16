create function insert_data(author_names text[], book_name text, year smallint) returns void as
$$
declare
    temp_book_id   bigint;
    temp_author_id bigint;
    author_name    text;
begin
    with new_book as (
        insert into book (publishing_year, title)
            values (year, book_name)
            on conflict (title) do nothing
            returning book_id
    )
    select coalesce(
                       (select book_id from new_book),
                       (select book_id from book where title = book_name)
               )
    into temp_book_id;

    foreach author_name in array author_names
        loop
            with new_author as (
                insert into author (name)
                    values (author_name)
                    on conflict (name) do nothing
                    returning author_id
            )
            select coalesce(
                               (select author_id from new_author),
                               (select author_id from author where name = author_name)
                       )
            into temp_author_id;

            insert into book_author (author_id, book_id)
            values (temp_author_id, temp_book_id);
        end loop;
end;
$$
    language plpgsql;

select insert_data(
               array ['Kathy Sierra', 'Bert Bates'],
               text 'Head First Java',
               cast(2005 as smallint)
           );

select insert_data(
               array ['Kathy Sierra', 'Bert Bates', 'Eric Freeman', 'Elisabeth Robson'],
               text 'Head First Design Patterns',
               cast(2004 as smallint)
           );

select insert_data(
               array ['Joshua Bloch'],
               text 'Effective Java',
               cast(2008 as smallint)
           );

select insert_data(
               array ['Brian Goetz'],
               text 'Java Concurrency in Practice',
               cast(2006 as smallint)
           );

select insert_data(
               array ['Craig Walls'],
               text 'Spring in Action, Fifth Edition',
               cast(2018 as smallint)
           );

select insert_data(
               array ['Dinesh Rajput'],
               text 'Spring 5 Design Patterns',
               cast(2017 as smallint)
           );

select insert_data(
               array ['John Carnell', 'Kalpit Patel'],
               text 'Spring Microservices in Action',
               cast(2017 as smallint)
           );

select insert_data(
               array ['Kenny Bastani', 'Josh Long'],
               text 'Cloud Native Java',
               cast(2017 as smallint)
           );

select insert_data(
               array ['Marten Deinum', 'Daniel Rubio', 'Josh Long'],
               text 'Spring 5 Recipes: A Problem-Solution Approach',
               cast(2017 as smallint)
           );

select insert_data(
               array ['Greg L. Turnquist'],
               text 'Learning Spring Boot 2.0 - Second Edition',
               cast(2017 as smallint)
           );

select insert_data(
               array ['Robert Martin'],
               text 'Clean Code: A Handbook of Agile Software Craftsmanship',
               cast(2008 as smallint)
           );

select insert_data(
               array ['Charlie Hunt'],
               text 'Java Performance',
               cast(2011 as smallint)
           );

select insert_data(
               array ['Brett McLaughlin', 'Gary Pollice', 'David West'],
               text 'Head First Object-Oriented Analysis and Design',
               cast(2006 as smallint)
           );