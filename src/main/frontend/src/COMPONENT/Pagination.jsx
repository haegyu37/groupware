import {Button} from "react-bootstrap";
import styled from "styled-components";

function Pagination({total, page, setPage, limit}) {

    const numPages = Math.ceil(total / limit);

    return (
        <Wrapper>
            <PageButton onClick={() => setPage(page - 1)} disabled={page === 1}>&lt;</PageButton>
            {Array(numPages).fill().map((_, i) => (
                <PageButton className={page === i + 1 ? "active" : ""}
                        key={i + 1}
                        onClick={() => setPage(i + 1)}
                        aria-current={page === i + 1 ? "page" : undefined}
                >
                    {i + 1}
                </PageButton>
            ))}
            <PageButton onClick={(()=> setPage(page+1))} disabled={page=== numPages}>&gt;</PageButton>
        </Wrapper>
    )
}
export default Pagination

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 6px;
  margin: 20px;
  padding-top: 30px;
`
const PageButton = styled(Button)`
  --bs-btn-bg: none;
  --bs-btn-color: #757575;
  --bs-btn-border-color: #757575;

  --bs-btn-hover-bg: #e1e1e1;
  --bs-btn-hover-border-color: #757575;
  --bs-btn-hover-color: #757575;
  
  --bs-btn-active-bg: #f3f34f;
  --bs-btn-active-color: #757575;
  --bs-btn-active-border-color: #757575;

  --bs-btn-disabled-bg: #757575;
  --bs-btn-disabled-border-color: #757575;
`