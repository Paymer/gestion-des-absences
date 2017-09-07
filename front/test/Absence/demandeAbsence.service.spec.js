describe('test demande absence $http Post', () => {
    beforeEach(() => {
        angular.mock.module('app')
    })
    // injection du mock $httpBackend
    it('mock du service $http', angular.mock.inject(($httpBackend, demandeAbsenceService,apiUrls)=> {
        // définition du comportement attendu de $http
        $httpBackend.whenPOST(apiUrls.demandeAbsence).respond([{"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"CONGE_SANS_SOLDE","idEmploye":3,"motif":"La tests motif"}]);
        
        $httpBackend.expectPOST(apiUrls.demandeAbsence,jasmine.objectContaining({"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"CONGE_SANS_SOLDE","idEmploye":3,"motif":"La tests motif"}));
     //   $httpBackend.flush(); // déclenche les réponses HTTP
    }))
})