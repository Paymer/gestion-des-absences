
export default class SuppressionFerieService {
    constructor($http, apiUrls) {
        this.$http = $http;
        this.apiUrls = apiUrls;
    }

    supprimerFerie(ferie) {
        return this.$http.delete(this.apiUrls.ferie, {data: ferie})
    }
}