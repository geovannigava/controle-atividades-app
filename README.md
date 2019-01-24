#Controle de Atividades

Sistema Web para controle de atividades

Exemplo da utilização das Tecnologias:

- Back-end: API Rest com a utilização de Java Servlets, JPA/Hibernate, Maven
- Front-end: Angular 6, Angular Material Framework
- Banco de dados: Postgres

Servidor de aplicação utilizado: Apache Tomcat

Aplicação disponível em : http://104.248.2.184:8080/atividades

Regras de negócio:

Os tipos de atividades podem ser: Desenvolvimento, Atendimento, Manutenção e Manutenção urgente;
 Atividades de manutenção urgente não podem ser removidas, apenas finalizadas;
 Atividades de atendimento e manutenção urgente não podem ser finalizadas se a descrição estiver preenchida com menos de 50 caracteres;
 Manutenções urgentes não podem ser criadas (nem via edição) após as 13:00 das sextas-feiras
