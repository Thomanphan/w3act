# --- !Ups

ALTER TABLE public.document ADD COLUMN title2 text;

ALTER TABLE public.book ADD COLUMN print_isbn character varying(255);
ALTER TABLE public.book ADD COLUMN  corporate_author_subordinate_unit character varying(255);
ALTER TABLE public.book ADD COLUMN  corporate_author2 character varying(255);
ALTER TABLE public.book ADD COLUMN  corporate_author2_subordinate_unit character varying(255);
ALTER TABLE public.book ADD COLUMN  corporate_author3 character varying(255);
ALTER TABLE public.book ADD COLUMN  corporate_author3_subordinate_unit character varying(255);
ALTER TABLE public.book ADD COLUMN  government_body character varying(255);
ALTER TABLE public.book ADD COLUMN  government_body_subordinate_unit character varying(255);
ALTER TABLE public.book ADD COLUMN  government_body2 character varying(255);
ALTER TABLE public.book ADD COLUMN  government_body2_subordinate_unit character varying(255);
ALTER TABLE public.book ADD COLUMN  government_body3 character varying(255);
ALTER TABLE public.book ADD COLUMN  government_body3_subordinate_unit character varying(255);
ALTER TABLE public.book ADD COLUMN  part_number character varying(255);
ALTER TABLE public.book ADD COLUMN  part_name character varying(255);

UPDATE public.mail_template
SET subject = 'UKWA Licence Received', updated_at = CURRENT_TIMESTAMP
WHERE subject = 'British Library UKWA Licence Received';

ALTER TABLE watched_target DROP COLUMN login_page_url;
ALTER TABLE watched_target DROP COLUMN logout_url;
ALTER TABLE watched_target DROP COLUMN secret_id;
ALTER TABLE target ADD COLUMN login_page_url text;
ALTER TABLE target ADD COLUMN logout_url text;
ALTER TABLE target ADD COLUMN secret_id integer;

ALTER TABLE public.document ADD COLUMN updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
CREATE INDEX ix_updated_at ON public.document(updated_at);

# --- !Downs

UPDATE public.mail_template
SET subject = 'British Library UKWA Licence Received', updated_at = CURRENT_TIMESTAMP
WHERE subject = 'UKWA Licence Received';

ALTER TABLE public.book DROP COLUMN print_isbn;
ALTER TABLE public.book DROP COLUMN  corporate_author_subordinate_unit;
ALTER TABLE public.book DROP COLUMN  corporate_author2;
ALTER TABLE public.book DROP COLUMN  corporate_author2_subordinate_unit;
ALTER TABLE public.book DROP COLUMN  corporate_author3;
ALTER TABLE public.book DROP COLUMN  corporate_author3_subordinate_unit;
ALTER TABLE public.book DROP COLUMN  government_body;
ALTER TABLE public.book DROP COLUMN  government_body_subordinate_unit;
ALTER TABLE public.book DROP COLUMN  government_body2;
ALTER TABLE public.book DROP COLUMN  government_body2_subordinate_unit;
ALTER TABLE public.book DROP COLUMN  government_body3;
ALTER TABLE public.book DROP COLUMN  government_body3_subordinate_unit;
ALTER TABLE public.book DROP COLUMN  part_number;
ALTER TABLE public.book DROP COLUMN  part_name;

ALTER TABLE public.document DROP COLUMN title2;

ALTER TABLE target DROP COLUMN login_page_url;
ALTER TABLE target DROP COLUMN logout_url;
ALTER TABLE target DROP COLUMN secret_id;
ALTER TABLE watched_target ADD COLUMN login_page_url text;
ALTER TABLE watched_target ADD COLUMN logout_url text;
ALTER TABLE watched_target ADD COLUMN secret_id integer;

DROP INDEX public.ix_updated_at;
ALTER TABLE public.document DROP COLUMN updated_at;
