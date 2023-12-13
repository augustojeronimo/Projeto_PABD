create database vendadindin;

use vendadindin;

create table dindin(
  sabor varchar(30),
    valor double(4,2),
    quantidadeEstoque int unsigned,
    primary key(sabor)
);

create table venda(
  idVenda int,
  valorTotal double(10,2),
    primary key(idVenda)
);

create table dindinsVendidos(
	idVenda int,
    idDindin int,
    quantidade int,
    foreign key (idVenda) references venda(idVenda),
    primary key(idVenda, idDindin)
);

select * from dindin;

drop table dindin;

insert into dindin values ('uva', 2, 12);
insert into dindin values ('morango', 3, 19);
insert into dindin values ('coco', 1, 30);
insert into dindin values ('abacaxi', 0, 60);
insert into dindin values ('chocolate', 3, -120);
insert into dindin values ('maracujá', 2.5, 44);