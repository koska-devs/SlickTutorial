-- Users schema

-- !Ups
CREATE TABLE public.User(
  id SERIAL NOT NULL,
  name TEXT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE public.Memo(
  id SERIAL NOT NULL,
  content TEXT NOT NULL,
  user_id INTEGER NOT NULL REFERENCES public.User(id),
  PRIMARY KEY (id)
);

CREATE TABLE public.Label(
  id SERIAL NOT NULL ,
  name TEXT NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE public.LabelMemo(
  id SERIAL NOT NULL,
  memo_id INTEGER NOT NULL REFERENCES public.Memo(id),
  tag_id INTEGER NOT NULL REFERENCES public.Label(id),
  PRIMARY KEY (id)
);


-- !Downs
DROP TABLE public.User CASCADE;
DROP TABLE public.Label CASCADE;
DROP TABLE public.Memo CASCADE;
DROP TABLE public.LabelMemo CASCADE;
