import Pagination from 'react-bootstrap/Pagination';
import styles from "../ReportDocument.module.css";

function Pagination({total, page, setPage, limit}) {


    const pageNum = Math.ceil(total / limit);

    return(
        <div className={styles.pagination}>
            <Pagination>
                <Pagination.First onClick={() => setPage(1)} disabled={page === 1}/>
                <Pagination.Prev onClick={() => setPage(page - 1)} disabled={page === 1}/>
                {Array(pageNum)
                    .fill()
                    .map((_, i) => (
                        <Pagination.Item
                            key={i + 1}
                            onClick={() => setPage(i + 1)}
                            aria-current={page === i + 1 && "page"}
                        >
                            {i + 1}
                        </Pagination.Item>
                    ))
                }
                <Pagination.Next onClick={() => setPage(page + 1)} disabled={page === pageNum}/>
                <Pagination.Last onClick={() => setPage(pageNum)} disabled={page === pageNum}/>
            </Pagination>
            {/*<Pagination.Ellipsis />*/}
        </div>
    )
}

export default Pagination