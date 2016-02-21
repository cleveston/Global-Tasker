--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: client; Type: TABLE; Schema: public; Owner: iury; Tablespace: 
--

CREATE TABLE client (
    idclient bigint NOT NULL,
    name character varying(255),
    login character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    datecreation timestamp without time zone NOT NULL,
    status character(1) NOT NULL,
    accesstoken character varying(255)
);


ALTER TABLE client OWNER TO iury;

--
-- Name: client_idclient_seq; Type: SEQUENCE; Schema: public; Owner: iury
--

CREATE SEQUENCE client_idclient_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE client_idclient_seq OWNER TO iury;

--
-- Name: client_idclient_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: iury
--

ALTER SEQUENCE client_idclient_seq OWNED BY client.idclient;


--
-- Name: process; Type: TABLE; Schema: public; Owner: iury; Tablespace: 
--

CREATE TABLE process (
    idprocess bigint NOT NULL,
    status character(1) NOT NULL,
    result json,
    datesend time without time zone,
    idclient bigint,
    idtask bigint NOT NULL,
    datedone timestamp without time zone,
    subtask json NOT NULL,
    score double precision NOT NULL
);


ALTER TABLE process OWNER TO iury;

--
-- Name: process_idprocess_seq; Type: SEQUENCE; Schema: public; Owner: iury
--

CREATE SEQUENCE process_idprocess_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE process_idprocess_seq OWNER TO iury;

--
-- Name: process_idprocess_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: iury
--

ALTER SEQUENCE process_idprocess_seq OWNED BY process.idprocess;


--
-- Name: score; Type: TABLE; Schema: public; Owner: iury; Tablespace: 
--

CREATE TABLE score (
    idscore bigint NOT NULL,
    score double precision NOT NULL,
    idprocess bigint NOT NULL,
    idclient bigint NOT NULL,
    datecreation timestamp without time zone NOT NULL
);


ALTER TABLE score OWNER TO iury;

--
-- Name: score_idscore_seq; Type: SEQUENCE; Schema: public; Owner: iury
--

CREATE SEQUENCE score_idscore_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_idscore_seq OWNER TO iury;

--
-- Name: score_idscore_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: iury
--

ALTER SEQUENCE score_idscore_seq OWNED BY score.idscore;


--
-- Name: task; Type: TABLE; Schema: public; Owner: iury; Tablespace: 
--

CREATE TABLE task (
    idtask bigint NOT NULL,
    task json NOT NULL,
    type character(1) NOT NULL,
    status character(1) NOT NULL,
    datecreation timestamp without time zone NOT NULL,
    result json,
    idclient bigint NOT NULL
);


ALTER TABLE task OWNER TO iury;

--
-- Name: task_idtask_seq; Type: SEQUENCE; Schema: public; Owner: iury
--

CREATE SEQUENCE task_idtask_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE task_idtask_seq OWNER TO iury;

--
-- Name: task_idtask_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: iury
--

ALTER SEQUENCE task_idtask_seq OWNED BY task.idtask;


--
-- Name: idclient; Type: DEFAULT; Schema: public; Owner: iury
--

ALTER TABLE ONLY client ALTER COLUMN idclient SET DEFAULT nextval('client_idclient_seq'::regclass);


--
-- Name: idprocess; Type: DEFAULT; Schema: public; Owner: iury
--

ALTER TABLE ONLY process ALTER COLUMN idprocess SET DEFAULT nextval('process_idprocess_seq'::regclass);


--
-- Name: idscore; Type: DEFAULT; Schema: public; Owner: iury
--

ALTER TABLE ONLY score ALTER COLUMN idscore SET DEFAULT nextval('score_idscore_seq'::regclass);


--
-- Name: idtask; Type: DEFAULT; Schema: public; Owner: iury
--

ALTER TABLE ONLY task ALTER COLUMN idtask SET DEFAULT nextval('task_idtask_seq'::regclass);


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: iury
--

COPY client (idclient, name, login, password, datecreation, status, accesstoken) FROM stdin;
\.


--
-- Name: client_idclient_seq; Type: SEQUENCE SET; Schema: public; Owner: iury
--

SELECT pg_catalog.setval('client_idclient_seq', 72, true);


--
-- Data for Name: process; Type: TABLE DATA; Schema: public; Owner: iury
--

COPY process (idprocess, status, result, datesend, idclient, idtask, datedone, subtask, score) FROM stdin;
\.


--
-- Name: process_idprocess_seq; Type: SEQUENCE SET; Schema: public; Owner: iury
--

SELECT pg_catalog.setval('process_idprocess_seq', 558, true);


--
-- Data for Name: score; Type: TABLE DATA; Schema: public; Owner: iury
--

COPY score (idscore, score, idprocess, idclient, datecreation) FROM stdin;
\.


--
-- Name: score_idscore_seq; Type: SEQUENCE SET; Schema: public; Owner: iury
--

SELECT pg_catalog.setval('score_idscore_seq', 185, true);


--
-- Data for Name: task; Type: TABLE DATA; Schema: public; Owner: iury
--

COPY task (idtask, task, type, status, datecreation, result, idclient) FROM stdin;
\.


--
-- Name: task_idtask_seq; Type: SEQUENCE SET; Schema: public; Owner: iury
--

SELECT pg_catalog.setval('task_idtask_seq', 171, true);


--
-- Name: client_pkey; Type: CONSTRAINT; Schema: public; Owner: iury; Tablespace: 
--

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (idclient);


--
-- Name: login_unique; Type: CONSTRAINT; Schema: public; Owner: iury; Tablespace: 
--

ALTER TABLE ONLY client
    ADD CONSTRAINT login_unique UNIQUE (login);


--
-- Name: process_pkey; Type: CONSTRAINT; Schema: public; Owner: iury; Tablespace: 
--

ALTER TABLE ONLY process
    ADD CONSTRAINT process_pkey PRIMARY KEY (idprocess);


--
-- Name: score_pkey; Type: CONSTRAINT; Schema: public; Owner: iury; Tablespace: 
--

ALTER TABLE ONLY score
    ADD CONSTRAINT score_pkey PRIMARY KEY (idscore);


--
-- Name: task_pkey; Type: CONSTRAINT; Schema: public; Owner: iury; Tablespace: 
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_pkey PRIMARY KEY (idtask);


--
-- Name: process_idclient_fkey; Type: FK CONSTRAINT; Schema: public; Owner: iury
--

ALTER TABLE ONLY process
    ADD CONSTRAINT process_idclient_fkey FOREIGN KEY (idclient) REFERENCES client(idclient);


--
-- Name: process_idtask_fkey; Type: FK CONSTRAINT; Schema: public; Owner: iury
--

ALTER TABLE ONLY process
    ADD CONSTRAINT process_idtask_fkey FOREIGN KEY (idtask) REFERENCES task(idtask);


--
-- Name: score_idclient_fkey; Type: FK CONSTRAINT; Schema: public; Owner: iury
--

ALTER TABLE ONLY score
    ADD CONSTRAINT score_idclient_fkey FOREIGN KEY (idclient) REFERENCES client(idclient);


--
-- Name: score_idprocess_fkey; Type: FK CONSTRAINT; Schema: public; Owner: iury
--

ALTER TABLE ONLY score
    ADD CONSTRAINT score_idprocess_fkey FOREIGN KEY (idprocess) REFERENCES process(idprocess);


--
-- Name: task_idclient_fkey; Type: FK CONSTRAINT; Schema: public; Owner: iury
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_idclient_fkey FOREIGN KEY (idclient) REFERENCES client(idclient);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

