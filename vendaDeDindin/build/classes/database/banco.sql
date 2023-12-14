create database vendadindin;
use vendadindin;


create table dindin(
	sabor varchar(70),
    valor double(4,2) unsigned,
    quantidadeEstoque int unsigned,
    primary key(sabor)
);

create table venda(
	idVenda int unsigned auto_increment,
	valorTotal double(10,2) unsigned,
    valida binary default(1),
    primary key(idVenda)

);

create table dindinsVendidos(
	idVenda int unsigned,
    saborDindin varchar(70),
    quantidade int unsigned,
    foreign key (idVenda) references venda(idVenda),
    foreign key (saborDindin) references dindin(sabor),
    primary key(idVenda, saborDindin)
);

select * from dindin;

select v.idVenda, v.valorTotal, sum(dv.quantidade) from venda as v,dindinsVendidos as dv
where v.idVenda = dv.idVenda and v.valida = 1 group by v.idVenda;

drop table dindinsVendidos;
drop table venda;
drop table dindin;

truncate table dindin;
truncate table venda;
truncate table dindinsVendidos;

insert into dindin values ('uva', 2, 12);
insert into dindin values ('morango', 3, 19);
insert into dindin values ('chocolate', 3, 0);

insert into venda values (default, 30, 0);
insert into dindinsVendidos values (1, 'morango', 5);
insert into dindinsVendidos values (1, 'chocolate', 5);
