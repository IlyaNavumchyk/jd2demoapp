create or replace function get_users_stats(is_deleted_param boolean) returns integer
    language sql
as
$$ select count(id)
   from carshop.users
   where is_deleted = is_deleted_param;
$$;

alter function get_users_stats(boolean) owner to postgres;

create or replace function find_user_by_name_and_surname(name_param text, surname_param text)
    returns TABLE(user_id bigint)
    language sql
as
$$ select id
   from carshop.users
   where user_name ilike '%'||name_param||'%' and surname ilike '%'||surname_param||'%'
$$;

alter function find_user_by_name_and_surname(text, text) owner to postgres;