-- Corrigindo problemas de enum
update cliente set escolaridade='ANALFABETO' where escolaridade = '1';
update cliente set escolaridade='PRIMEIRO_GRAU_INCOMPLETO' where escolaridade = '2';
update cliente set escolaridade='PRIMEIRO_GRAU_COMPLETO' where escolaridade = '3';
update cliente set escolaridade='SEGUNDO_GRAU_INCOMPLETO' where escolaridade = '4';
update cliente set escolaridade='SEGUNDO_GRAU_COMPLETO' where escolaridade = '5';
update cliente set escolaridade='SUPERIOR_INCOMPLETO' where escolaridade = '6';
update cliente set escolaridade='SUPERIOR_COMPLETO' where escolaridade = '7';
update cliente set escolaridade='POS_GRADUACAO' where escolaridade = '8';
update cliente set escolaridade='OUTROS' where escolaridade = '99';
update cliente set escolaridade='OUTROS' where escolaridade is null;