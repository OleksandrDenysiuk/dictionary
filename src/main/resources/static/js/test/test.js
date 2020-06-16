window.addEventListener('DOMContentLoaded', function () {

    const cardContainer = document.querySelector('.cards-container'),
        categoriesID = JSON.parse(sessionStorage.getItem('categories')),
        result = {
            categoriesId: categoriesID,
            words: [],
            answers: []
        };

    cardContainer.innerHTML = '';

    categoriesID.forEach(id => {
        getResource(`/api/categories/${id}/words`)
            .then(words => {
                words.forEach(word => {
                    createCard(word.word, cardContainer, result);
                });
                return cardContainer.querySelectorAll('.card');
            })
            .then((cards) => {
                if (cards[0].classList.contains('d-none')) {
                    cards[0].classList.toggle('d-none');
                }
            });
    });


});

function createCard(word, parentSelector, result) {
    const card = document.createElement('div');

    card.style.width = '40rem';

    card.classList.add('card', 'mr-auto', 'ml-auto', 'mt-5', 'mb-5', 'd-none');

    card.innerHTML = `
        <div class="card-body">
            <div class="form-group row">
                <label for="word" class="col-sm-3 col-form-label ">Word</label>
                <div class="col-sm-9">
                    <input type="text" readonly class="form-control-plaintext card-title" id="word" value="${word}">
                </div>
            </div>
            <div class="form-group row">
                <label for="translation" class="col-sm-3 col-form-label">Translation</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="translation">
                </div>
            </div>
            <div class="text-center">
                <button class="btn btn-primary" id="nextQuestion">Next word</button>
            </div>
        </div>`;

    const nextBtn = card.querySelector('button'),
        input = card.querySelectorAll('input')[1];
    nextBtn.addEventListener('click', () => {
        const nextElement = card.nextElementSibling;
        result.words.push(word);
        result.answers.push(input.value);
        if (nextElement !== null) {
            nextElement.classList.toggle('d-none');
            card.remove();
        } else {
            result.startTime = sessionStorage.getItem('startTime');
            result.finishTime = JSON.stringify(new Date());
            result.testType = sessionStorage.getItem('type');
            console.log(result);
            postData('/api/results', JSON.stringify(result))
                .then((data) => {
                    console.log(data);
                    window.location.replace(`/tests/results/${data.id}`);
                });
        }
    });

    parentSelector.append(card);
}

const getResource = async (url) => {
    const res = await fetch(url);

    if (!res.ok) {
        throw new Error(`Could not fetch ${url}, status: ${res.status}`);
    }
    return await res.json();
};

const postData = async (url, data) => {
    const res = await fetch(url, {
        method: "POST",
        headers: {
            'Content-type': 'application/json; charset=utf-8'
        },
        body: data
    });

    return await res.json();
};