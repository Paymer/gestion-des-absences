export default class ModifAbsenceService{

constructor(apiUrls, $resource){
    this.url = apiUrls.modifAbsence;
    this.$resource = $resource;
    this.modifAbsenceRessource = this.$resource(this.url, {absenceId: "@id"}, {
        update: { method: "PUT" }
    })
}

confirmeEnvoiAbsence(absence){
    this.modifAbsenceRessource.update(absence);
   
}


}