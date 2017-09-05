const API_URL = process.env.NODE_ENV === 'production' ? 'https://api-absences.cleverapps.io' : 'http://localhost:8080';

const apiUrls = {
    absence : API_URL + "/absence"
}

export default apiUrls