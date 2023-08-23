CREATE TABLE public.tags (
	id uuid NOT NULL,
	account uuid NOT NULL,
	"name" varchar(20) NOT NULL,
	CONSTRAINT tags_pkey PRIMARY KEY (id)
);

CREATE TABLE public.transactions (
	id uuid NOT NULL,
	"type" VARCHAR(20) NULL,
	account uuid NOT NULL,
	"transaction_at" timestamp NULL,
	"name" varchar(50) NOT NULL,
	description varchar(200) NOT NULL,
	price numeric(11, 2) NOT NULL,
	"removed_at" timestamp NULL,
	CONSTRAINT transactions_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_transaction_account ON transactions(account);

CREATE TABLE public.transaction_tags (
	transaction_id uuid NULL,
	tag_id uuid NULL,
	CONSTRAINT transaction_tags_pkey PRIMARY KEY (transaction_id, tag_id),
	CONSTRAINT transaction_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tags(id),
	CONSTRAINT transaction_tags_transaction_id_fkey FOREIGN KEY (transaction_id) REFERENCES public.transactions(id)
);