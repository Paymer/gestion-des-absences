INSERT INTO absence (date_debut, date_fin, type, matricule_employe, motif, statut) VALUES
  ('2017-05-01', '2017-05-04', 'RTT', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-05-03', '2017-05-10', 'RTT', '56eb7d01', 'Maladie youpi', 'INITIALE'),
  ('2017-05-29', '2017-05-31', 'RTT', '56eb7d01', 'Maladie youpi', 'INITIALE'),
  ('2017-05-11', '2017-05-18', 'RTT', '37db541d', 'Maladie youpi', 'INITIALE'),
  ('2017-10-10', '2017-10-12', 'RTT', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-05-15', '2017-05-19', 'CONGES_PAYES', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-05-25', '2017-05-26', 'MISSION', '6c8be60e', 'Maladie youpi', 'INITIALE'),
  ('2017-06-02', '2017-06-06', 'RTT', '7befca85', 'Un motif', 'EN_ATTENTE_VALIDATION'),
  ('2017-07-03', '2017-07-12', 'CONGES_PAYES', '89dde79f', 'Un autre motif', 'VALIDEE'),
  ('2017-08-04', '2017-08-9', 'RTT', 'a8fc21fc', 'Plus d"idée', 'REJETEE'),
  ('2017-09-03', '2017-09-21', 'CONGES_SANS_SOLDE', 'e300fb12', 'La non plus non plus', 'VALIDEE'),
  ('2017-10-04', '2017-10-23', 'CONGES_SANS_SOLDE', 'f26eac86', 'Quelqu"un a une idée ?', 'INITIALE'),
  ('2017-11-06', '2017-11-24', 'MISSION', '740ef3fd', 'Je galère... :-(', 'VALIDEE'),
  ('2017-12-06', '2017-12-24', 'CONGES_PAYES', 'e353c695', 'Je galère... :-(', 'EN_ATTENTE_VALIDATION'),
  ('2017-12-06', '2017-12-24', 'MISSION', 'bd540e65', 'Je galère... :-(', 'INITIALE'),
  ('2017-10-06', '2017-10-21', 'MISSION', '37db541d', 'Je galère... :-(', 'INITIALE'),
  ('2017-09-03', '2017-09-08', 'CONGES_PAYES', '37db541d', 'Je galère... :-(', 'INITIALE'),
  ('2017-12-06', '2017-12-10', 'RTT', '37db541d', 'Je galère... :-(', 'INITIALE'),
  ('2017-05-06', '2017-05-12', 'CONGES_PAYES', '478389f2', 'Je galère... :-(', 'INITIALE'),
  ('2017-09-14', '2017-09-19', 'RTT', '478389f2', 'Je galère... :-(', 'INITIALE'),
  ('2017-10-04', '2017-10-10', 'CONGES_PAYES', '478389f2', 'Je galère... :-(', 'INITIALE'),
  
    ('2017-05-17', '2017-05-23', 'RTT', 'e300fb12', 'Maladie youpi', 'INITIALE'),
    ('2017-05-17', '2017-05-23', 'RTT', '8dd0b708', 'Maladie youpi', 'INITIALE'),
    ('2017-05-17', '2017-05-23', 'RTT', 'e353c695', 'Maladie youpi', 'INITIALE'),
    ('2017-05-17', '2017-05-23', 'RTT', '26a79080', 'Maladie youpi', 'INITIALE'),
    ('2017-05-17', '2017-05-23', 'RTT', 'ede47266', 'Maladie youpi', 'INITIALE'),
    
    ('2017-05-01', '2017-05-15', 'RTT', 'e300fb12', 'Maladie youpi', 'INITIALE'),
    ('2017-05-01', '2017-05-12', 'RTT', '8dd0b708', 'Maladie youpi', 'INITIALE'),
    
    ('2017-05-26', '2017-05-31', 'RTT', 'e353c695', 'Maladie youpi', 'INITIALE'),
    ('2017-05-26', '2017-05-31', 'RTT', '26a79080', 'Maladie youpi', 'INITIALE'),
     ('2017-05-26', '2017-05-31', 'RTT', 'ede47266', 'Maladie youpi', 'INITIALE'),

  
  ('2016-05-01', null, 'JOUR_FERIE', null, 'Fête du travail', 'VALIDEE'),
  ('2017-07-14', null, 'JOUR_FERIE', null, 'Fête nationale', 'VALIDEE'),
  ('2017-05-02', null, 'RTT_EMPLOYEUR', null, 'Pont 1', 'INITIALE'),
  ('2016-07-13', null, 'RTT_EMPLOYEUR', null, 'Pont 2', 'VALIDEE');

INSERT INTO message_erreur (date, service_origine, message) VALUES
('2015-05-14 00:00:08', 'Traitement de nuit', 'message 1'),
('2016-08-30 01:01:01', 'Traitement de nuit', 'message 2'),
('2016-12-11 00:00:07', 'Traitement de nuit', 'message 3'),
('2017-01-18 00:00:03', 'Traitement de nuit', 'message 4'),
('2017-01-01 00:00:02', 'Traitement de nuit', 'message 5'),
('2017-03-17 00:00:01', 'Traitement de nuit', 'message 6'),
('2017-09-12 01:00:00', 'Traitement de nuit', 'message 7');
