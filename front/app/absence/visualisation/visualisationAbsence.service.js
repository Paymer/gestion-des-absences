
export default class VisualisationAbsenceService {
    constructor(apiUrls, $resource) {
        this.apiUrls = apiUrls;
        this.$resource = $resource;

        this.absencesRes = this.$resource(this.apiUrls.absence);
        this.absences = this.absencesRes.query();
    }

    findAll() {
        return this.absences;
    }
}