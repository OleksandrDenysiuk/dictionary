window.addEventListener('DOMContentLoaded', function () {
    const resultCardBody = document.querySelector('.cards-container .card-body'),
        resultId = document.querySelector('.cards-container').dataset.resultId;
    const getResource = async (url) => {
        const res = await fetch(url);

        if (!res.ok) {
            throw new Error(`Could not fetch ${url}, status: ${res.status}`);
        }
        return await res.json();
    };

    getResource(`/api/results/${resultId}`)
        .then(result => {
            resultCardBody.innerHTML = '';
            const element = document.createElement('div');
            element.innerHTML = `
               <h5 class="card-title">Start time:</h5>
               <p class="card-text">${result.startTime}</p>
               <h5 class="card-title">Finish time:</h5>
               <p class="card-text">${result.finishTime}</p>
               <h5 class="card-title">Test type:</h5>
               <p class="card-text">${result.testType}</p>
               <h5 class="card-title">Points:</h5>
               <p class="card-text">${result.points}/10</p>
                <div class="text-center">
                    <a href="/" class="btn btn-primary">Okay!</a>
                </div>`;
            resultCardBody.append(element);
        })
});