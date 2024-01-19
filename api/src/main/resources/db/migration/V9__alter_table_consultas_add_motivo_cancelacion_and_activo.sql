alter table consultas
add motivo_cancelacion varchar (300) null;

alter table consultas
add activo tinyint not null;

update consultas set activo = 1;