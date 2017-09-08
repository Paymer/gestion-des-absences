export default class ModifAbsenceService{

constructor(apiUrls,$resource){
    this.url = apiUrls.modifAbsence;
    this.$resource = $resource;
    this.modifAbsenceRessource = this.$resource(this.url, {absenceId: "@id"}, {
        update: { method: "PUT" }
    })
}

confirmeEnvoiAbsence(absence){
    return this.modifAbsenceRessource.update(absence).$promise;
   
}


}