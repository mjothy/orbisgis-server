INSERT INTO user (name,email,password,location,profession,additional) VALUES ('toto','toto@toto.com','','Nantes','dev','');
INSERT INTO WORKSPACE (id_creator, name, all_read, all_write, all_manage, description) VALUES (1,'name',1,1,1,'description');
INSERT INTO WORKSPACE (id_creator, name, all_read, all_write, all_manage, description) VALUES (1,'namesrc',1,1,1,'description');
INSERT INTO WORKSPACE (id_creator, name, all_read, all_write, all_manage, description) VALUES (1,'namesrc',1,1,1,'description');
INSERT INTO folder (id_root,id_parent,name) VALUES (1 , null , 'name');
INSERT INTO folder (id_root,id_parent,name) VALUES (1 , null , 'namesrc');
INSERT INTO owscontext (id_root,id_parent,id_uploader, title, content) VALUES (1, null, 1, 'title', 'hi');
INSERT INTO owscontext (id_root,id_parent,id_uploader, title) VALUES (1, null, 1, 'tyui');
INSERT INTO owscontext (id_root,id_parent,id_uploader, title) VALUES (1, null, 1, 'tyui');
INSERT INTO comment (id_writer,id_map,content,title) VALUES (1,1,'plop','title');
INSERT INTO comment (id_writer,id_map,content,title) VALUES (1,1,'plop','title');