describe('Test du service visualisationAbsence', () => {
    
        beforeEach(() => {
            angular.mock.module('app')
        })

        it('On vérifie que la méthode ajoutActions() ajoute bien un tableau d\'actions à chaque absence', angular.mock.inject((visualisationAbsenceService) => {
            
            let today = new Date();
            let dateDebut = new Date(today.getFullYear(), today.getMonth(), today.getDate()+1);
            let dateFin = new Date(today.getFullYear(), today.getMonth(), today.getDate()+5);
            

            visualisationAbsenceService.absences = [
                {"dateDebut":dateDebut,"dateFin":dateFin,"type":"CONGES_SANS_SOLDE","statut":"INITIALE","matriculeEmploye":"6c8be60e","motif":"Le motif test"},
                {"dateDebut":dateDebut,"dateFin":dateFin,"type":"RTT","statut":"VALIDEE","matriculeEmploye":"89dde79f","motif":"Le motif test"},
                {"dateDebut":dateDebut,"dateFin":dateFin,"type":"MISSION","statut":"EN_ATTENTE_VALIDATION","matriculeEmploye":"7befca85","motif":"Le motif test"}
            ]
            

            visualisationAbsenceService.ajoutActions(visualisationAbsenceService.absences);
            expect(visualisationAbsenceService.absences[0].actions).toContain("modification");
            expect(visualisationAbsenceService.absences[0].actions).toContain("suppression");
            expect(visualisationAbsenceService.absences[1].actions).toContain("suppression");
            expect(visualisationAbsenceService.absences[2].actions).toContain("visualisation");
        }))
})