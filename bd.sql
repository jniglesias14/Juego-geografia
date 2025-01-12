CREATE USER geografia1@localhost IDENTIFIED BY 'A12345a';
grant all on geografia.* to geografia1@localhost;
use geografia;
select * from localidades;

create table comunidades_elegidas(

                                     nombre varchar(50),

                                     constraint comunidades_elegidas_pk primary key (nombre)
);
insert into comunidades_elegidas (nombre) values ('Madrid');
create table juego_localidades(
                                  nombre varchar(50),
                                  aciertos int,
                                  fallos int,
                                  constraint juego_localidades_pk primary key (nombre)
);
select * from juego_localidades;
drop table comunidades_elegidas;
insert into comunidades_elegidas(nombre) value ('Madrid');
delete from comunidades_elegidas where nombre='Madrid';
select l.nombre,p.nombre,c.nombre
from localidades l
         join provincias p using (n_provincia)
         join comunidades c using(id_comunidad)
where l.poblacion>50000 and c.nombre in (select nombre from comunidades_elegidas);

select nombre
from juego_localidades
where aciertos=0
order by rand()
limit 1;