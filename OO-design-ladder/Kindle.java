import java.util.*;

public class Kindle {

    private List<Book> books;
    private EBookReaderFactory readerFactory;
    
	public Kindle() {
        this.books = new ArrayList<>();
        this.readerFactory = new EBookReaderFactory();
	}

	public String readBook(final Book book) throws Exception {
        EBookReader r = this.readerFactory.createReader(book);
        if (r == null)
            throw new Exception("Uncognized book format: " + book.getFormat());
        return r.displayReaderType() + ", book content is: " + r.readBook();
	}

	public void downloadBook(final Book b) {
		this.books.add(b);
	}

	public void uploadBook(final Book b) {
		this.books.add(b);
	}

	public void deleteBook(final Book b) {
		this.books.remove(b);
	}
}

enum Format {
	EPUB("epub"), PDF("pdf"), MOBI("mobi");

	private String content;

	Format(final String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}

class Book {
	private final Format format;

	public Book(final Format format) {
		this.format = format;
	}

	public Format getFormat() {
		return format;
	}
}

abstract class EBookReader {
	
	protected Book book;
	
	public EBookReader(final Book b){
		this.book = b;
	}
	
	public abstract String readBook();
	public abstract String displayReaderType();
}

class EBookReaderFactory {

	public EBookReader createReader(final Book b) {
		if (b.getFormat() == Format.EPUB)
            return new EpubReader(b);
        if (b.getFormat() == Format.PDF)
            return new PdfReader(b);
        if (b.getFormat() == Format.MOBI)
            return new MobiReader(b);
        return null;
	}
}

class EpubReader extends EBookReader{

	public EpubReader(final Book b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String readBook() {
		return this.book.getFormat().getContent();
	}

	@Override
	public String displayReaderType() {
		// TODO Auto-generated method stub
		return "Using EPUB reader";
	}
}

class MobiReader extends EBookReader {

	public MobiReader(final Book b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String readBook() {
		return this.book.getFormat().getContent();
	}

	@Override
	public String displayReaderType() {
		// TODO Auto-generated method stub
		return "Using MOBI reader";
	}

}
class PdfReader extends EBookReader{

	public PdfReader(final Book b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String readBook() {
		return this.book.getFormat().getContent();
	}

	@Override
	public String displayReaderType() {
		// TODO Auto-generated method stub
		return "Using PDF reader";
	}
}