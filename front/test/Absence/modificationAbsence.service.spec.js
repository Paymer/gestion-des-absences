
describe('test demande absence $http PUT', () => {
    beforeEach(() => {
        angular.mock.module('app')
    })

    // injection du mock $httpBackend
    it('mock du service $http', angular.mock.inject(($httpBackend, demandeAbsenceService,modificationAbsenceService, apiUrls)=> {
        
        
        
        // définition du comportement attendu de $http
        $httpBackend.when('PUT', apiUrls.modifAbsence).respond([{"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"CONGE_SANS_SOLDE","idEmploye":"3","motif":"La tests motif"}]);
        
        $httpBackend.expect('PUT',apiUrls.modifAbsence,jasmine.objectContaining({"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"CONGE_SANS_SOLDE","idEmploye":"3","motif":"La tests motif"}));
     //   $httpBackend.flush(); // déclenche les réponses HTTP
    }))
})