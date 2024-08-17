CREATE TABLE public.accounts (
	id uuid NOT NULL,
	"name" varchar(50) NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id)
);