window.addEventListener('DOMContentLoaded', function () {

    const categoryName = document.querySelector('.alert h4'),
        categoryId = categoryName.dataset.categoryId,
        wordsTableBody = document.querySelector('.table tbody'),
        categoryUpdateForm = document.querySelector('#formChangeCategoryNameModal form'),
        deleteCategoryBtn = document.querySelectorAll('.btn-outline-primary')[2],
        wordForm = document.querySelector('#formAddWordModal form'),
        addWordBtn = document.querySelector('#addWordButton');

    getResource(`http://localhost:8080/api/categories/${categoryId}`)
        .then(data => {
            console.log(data);

            //set category name
            categoryName.innerHTML = `${data.name}`;

            //render words table
            wordsTableBody.innerHTML = '';
            if(data.words.length === 0){
                wordsTableBody.innerHTML =`
                    <div class="alert alert-light text-center mb-0" role="alert">
                        <h5>Category is empty</h5>
                    </div>`;
            }else{
                data.words.forEach(({id, word, translation, categoryId}) => {
                    renderTable(categoryId, id, word, translation, wordsTableBody);
                });
            }

            const createWord = (e) => {
                e.preventDefault();

                const formData = new FormData(wordForm);

                const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

                postData(`/api/categories/${data.id}/words`, jsonData)
                    .then(() => {
                        location.reload();
                    })
            };


            //form to update category
            categoryUpdateForm.querySelector('input').value = data.name;

            categoryUpdateForm.addEventListener('submit', (e) => {
                e.preventDefault();

                const formData = new FormData(categoryUpdateForm);

                const jsonObject = JSON.stringify(Object.fromEntries(formData.entries()));

                updateData(`/api/categories/${data.id}`, jsonObject)
                    .then(() => {
                        location.reload();
                    });

            });

            //delete category event
            deleteCategoryBtn.addEventListener('click', () => {
                deleteData(`/api/categories/${data.id}`)
                    .then(() => {
                        location.assign('/');
                    });
            });

            //form add word
            wordForm.addEventListener('submit', createWord);

            //open editWord modal event
            wordsTableBody.addEventListener('click', (e) => {
                const target = e.target;
                if(target && target.classList.contains('btn-primary')){
                    const formInputs = wordForm.querySelectorAll('.form-group input');
                    formInputs[0].value = target.dataset.word;
                    formInputs[1].value = target.dataset.translation;
                    wordForm.dataset.wordId = `${target.dataset.id}`;
                }

                wordForm.removeEventListener('submit', createWord);
                //edit word event
                wordForm.addEventListener('submit', (e) => {
                    e.preventDefault();

                    const formData = new FormData(wordForm);
                    const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

                    updateData(`/api/categories/${data.id}/words/${wordForm.dataset.wordId}`, jsonData)
                        .then(() => {
                            location.reload();
                        });
                })
            });
        });
});

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

const updateData = async (url, data) => {
    const res = await fetch(url, {
        method: "PUT",
        headers: {
            'Content-type': 'application/json; charset=utf-8'
        },
        body: data
    });

    return await res.json();
};

const deleteData = async (url) => {
    return await fetch(url, {
        method: "DELETE"
    });
};

function renderTable(categoryId, wordId, word, translation, parentSelector) {
    const element = document.createElement('tr'),
            deleteWordHref = document.createElement('a');
    deleteWordHref.href = '#';
    deleteWordHref.innerHTML = 'Delete';

    //delete word event
    deleteWordHref.addEventListener('click', (e) => {
        e.preventDefault();

        deleteData(`/api/categories/${categoryId}/words/${wordId}`)
            .then(() => {
                location.reload();
            })
    });

    //render table
    element.innerHTML = `
                <td>${word}</td>
                <td>${translation}</td>
                <td><button type="button" class="btn btn-primary" data-target="#formAddWordModal" data-toggle="modal"
                        data-id = "${wordId}"
                        data-word="${word}"
                        data-translation="${translation}">Edit</button></td>
                <td></td>`;

    element.querySelectorAll('td')[3].append(deleteWordHref);
    parentSelector.append(element);
}