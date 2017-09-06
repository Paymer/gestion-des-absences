export default class ModifAbsenceService{

constructor(apiUrls,$resource){
    this.url = apiUrls.modifAbsence;
    this.modifAbsenceRessource = $resource(this.url,{
        save: { method: "PUT" }
    })
}

confirmeEnvoiAbsence(absence){
    this.modifAbsenceRessource.save(absence);
}


}