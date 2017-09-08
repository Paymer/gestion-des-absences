describe('Test du service visualisationAbsence', () => {
    
        beforeEach(() => {
            angular.mock.module('app')
        })

        it('On vérifie que la méthode ajoutActions() ajoute bien un tableau d\'actions à chaque absence', angular.mock.inject((visualisationAbsenceService) => {
            
            visualisationAbsenceService.absences = [
                {"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"CONGES_SANS_SOLDE","statut":"INITIALE","matriculeEmploye":"6c8be60e","motif":"Le motif test"},
                {"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"RTT","statut":"VALIDEE","matriculeEmploye":"89dde79f","motif":"Le motif test"},
                {"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"MISSION","statut":"EN_ATTENTE_VALIDATION","matriculeEmploye":"7befca85","motif":"Le motif test"}
            ]
            

            visualisationAbsenceService.ajoutActions(visualisationAbsenceService.absences);
            expect(visualisationAbsenceService.absences[0].actions).toContain("modification");
            expect(visualisationAbsenceService.absences[0].actions).toContain("suppression");
            expect(visualisationAbsenceService.absences[1].actions).toContain("suppression");
            expect(visualisationAbsenceService.absences[2].actions).toContain("visualisation");
        }))
})