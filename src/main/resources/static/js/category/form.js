window.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('#formCategory'),
        userName = document.querySelector('.navbar span'),
        categoriesRow = document.querySelectorAll('.row')[1];

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const formData = new FormData(form);

        const jsonObject = JSON.stringify(Object.fromEntries(formData.entries()));

        postData('http://localhost:8080/api/categories', jsonObject)
            .then(() => {
                location.reload();
            });
    });

    getResource('http://localhost:8080/api/categories')
        .then(data => {
            categoriesRow.innerHTML = '';
            if (data.length === 0) {
                categoriesRow.innerHTML = `
                <div class="alert alert-light" role="alert">
                    You have no categories yet!
                </div>`
            }else{
                data.forEach(({id, name, userId, words}) => {
                    createCategoryCard(id, name, words.length, categoriesRow);
                });
            }
        });

});

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

const getResource = async (url) => {
    const res = await fetch(url);

    if (!res.ok) {
        throw new Error(`Could not fetch ${url}, status: ${res.status}`);
    }
    return await res.json();
};

function createCategoryCard(id, title, amount, selector) {
    const cardWrapper = document.createElement('div');
    cardWrapper.classList.add('col-md-4', 'mb-3', 'mt-3');
    cardWrapper.innerHTML = `
        <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center">${title}</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Amount word: ${amount}</span></li>
                </ul>
                <div class="card-body">
                    <a href="/category/${id}" class="card-link">More</a>
                </div>
            </div>
        `;

    selector.append(cardWrapper);

}