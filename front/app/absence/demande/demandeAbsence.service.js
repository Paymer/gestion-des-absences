export default class demandeAbsenceService{

constructor(apiUrls,$resource){
    this.url = apiUrls.demandeAbsence;
    this.demandeAbsenceRessource = $resource(this.url,{
        save: { method: "POST" }
    })
}

confirmeEnvoiAbsence(absence){
    this.demandeAbsenceRessource.save(absence);
}


}