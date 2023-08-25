import styles from "../APPROVAL/Write.module.css"
import {useEffect, useRef, useState} from "react";
import fetcher from "../../fetcher";
import {
    CATEGORY_CREATE_API, CATEGORY_DELETE_API, CATEGORY_LIST_API,
    SHOW_CATEGORY_API, UPDATE_CATEGORY_API
} from "../../constants/api_constans";
import Dropdown from "react-bootstrap/Dropdown";
import Swal from "sweetalert2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import {Button} from "react-bootstrap";

function DocumentRegistration() {
    const [categoryName, setCategoryName] = useState("")
    const [content, setContent] = useState("")
    const [category, setCategory] = useState("")
    const [categoryList, setCategoryList] = useState([])

    const fetchCategoryList = () => {
        return fetcher.get(CATEGORY_LIST_API)
            .then((res) => setCategoryList(res.data))
    }
    useEffect(() => {
        fetchCategoryList()
    }, [])

    const resetInput = () => {
        setCategoryName("")
        setContent("")
    }

    const createCategory = () => {
        if (categoryName) {
            fetcher.post(CATEGORY_CREATE_API, {
                category: categoryName,
                content: content
            })
                .then(() => {
                    Swal.fire({
                        position: 'mid',
                        icon: 'success',
                        title: '양식 등록 완료.',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    resetInput()
                    fetchCategoryList()
                })
        } else (
            Swal.fire({
                title: "양식명을 입력 하세요",
                icon: 'warning',
            })
        )
    }
    const updateCategory = (id, categoryName) => {
        if (id) {
            fetcher.put(`${UPDATE_CATEGORY_API}/${id}`, {
                category: categoryName,
                content: content
            })
                .then(() => {
                    Swal.fire({
                        position: 'mid',
                        icon: 'success',
                        title: '양식 수정 완료.',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    resetInput()
                    fetchCategoryList()
                })
        } else (
            Swal.fire({
                title: "양식을 선택하세요.",
                icon: 'warning',
            })
        )
    }

    const selectCategory = (id) => {
        fetcher.get(`${SHOW_CATEGORY_API}/${id}`)
            .then((res) => {
                setCategory(res.data)
                setContent(res.data.content)
            })
    }
    const deleteCategory = (id) => {
        if (id) {
            fetcher.delete(`${CATEGORY_DELETE_API}/${id}`)
                .then(() => {
                    Swal.fire({
                        position: 'mid',
                        icon: 'success',
                        title: '양식 삭제 완료',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    fetchCategoryList()
                    setCategory("")
                    setContent("")
                })
        } else (
            Swal.fire({
                title: "양식을 선택하세요.",
                icon: 'warning',
            })
        )
    }

    return (
        <div className={styles.wrapper}>
            <div className={styles.upperContainer}>
                <div className={styles.select}>
                    <input value={categoryName}
                           onChange={(e) => setCategoryName(e.target.value)}
                           placeholder="양식명"
                    />
                    <Button className="buttonAdmin" onClick={createCategory}>양식 등록</Button>
                </div>
                <div className={styles.buttonGroup}>
                    <Dropdown>
                        <Dropdown.Toggle className="buttonAdmin">
                            {category ? category.category : "문서양식"}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            {categoryList?.map((data) => {
                                return (
                                    <Dropdown.Item key={data.id} value={data.category}
                                                   onClick={() => selectCategory(data.id)}>
                                        {data.category}
                                    </Dropdown.Item>
                                )
                            })}
                        </Dropdown.Menu>
                    </Dropdown>
                    <Button className="buttonAdmin" onClick={() => updateCategory(category.id, category.category)}>양식
                        수정</Button>
                    <Button className="buttonAdmin" onClick={() => deleteCategory(category.id)}>양식 삭제</Button>
                </div>
            </div>
            <div className={styles.divisionLine}></div>
            <div className={styles.lowerContainer}>
                <CKEditor
                    editor={ClassicEditor}
                    data={content}
                    config={{placeholder: "양식을 작성 하세요."}}
                    onChange={(event, editor) => {
                        const data = editor.getData();
                        setContent(data)
                    }}
                />
            </div>

        </div>
    )
}

export default DocumentRegistration