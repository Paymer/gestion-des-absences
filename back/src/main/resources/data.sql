INSERT INTO absence (date_debut, date_fin, type, matricule_employe, motif, statut) VALUES
  ('2017-05-01', '2017-05-04', 'RTT', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-10-10', '2017-10-12', 'RTT', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-05-15', '2017-05-19', 'CONGES_PAYES', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-05-25', '2017-05-26', 'MISSION', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-06-02', '2017-06-06', 'RTT', '7befca85', 'Un motif', 'EN_ATTENTE_VALIDATION'),
  ('2017-07-03', '2017-07-12', 'CONGES_PAYES', '89dde79f', 'Un autre motif', 'VALIDEE'),
  ('2017-08-04', '2017-08-15', 'RTT', 'a8fc21fc', 'Plus d"idée', 'REJETEE'),
  ('2017-09-03', '2017-09-21', 'CONGES_SANS_SOLDE', 'e300fb12', 'La non plus non plus', 'VALIDEE'),
  ('2017-10-04', '2017-10-23', 'CONGES_SANS_SOLDE', 'f26eac86', 'Quelqu"un a une idée ?', 'INITIALE'),
  ('2017-11-06', '2017-11-24', 'MISSION', '740ef3fd', 'Je galère... :-(', 'VALIDEE'),
  ('2017-12-06', '2017-12-24', 'CONGES_PAYES', 'e353c695', 'Je galère... :-(', 'EN_ATTENTE_VALIDATION');

INSERT INTO message_erreur (date, service_origine, message) VALUES
('2015-05-14 00:00:08', "Traitement de nuit", "message 1"),
('2016-08-30 01:01:01', "Traitement de nuit", "message 2"),
('2016-12-11 00:00:07', "Traitement de nuit", "message 3"),
('2017-01-18 00:00:03', "Traitement de nuit", "message 4"),
('2017-01-01 00:00:02', "Traitement de nuit", "message 5"),
('2017-03-17 00:00:01', "Traitement de nuit", "message 6"),
('2017-09-12 01:00:00', "Traitement de nuit", "message 7");