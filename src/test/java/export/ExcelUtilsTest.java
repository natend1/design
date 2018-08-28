package export;

import com.nieat.excelutils.ReaderInterface;
import com.nieat.excelutils.ExcelType;
import com.nieat.excelutils.ExcelUtils;
import com.nieat.vo.ClassVo;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 11:29 2018/8/23
 */
public class ExcelUtilsTest {
    @Test
    public void Reader() throws IOException {
        InputStream in = new FileInputStream("D:\\tmp\\template2.xlsx");
        ReaderInterface reader = ExcelUtils.newReaderExcel(ExcelType.XLSX, ClassVo.class);
        reader.read(in);
        List<Object> l = reader.getData();
        l.forEach((Object o) -> {
            ClassVo v = (ClassVo) o;
            System.out.println(v.toString());
        });
    }

    @Test
    public void write() throws Exception {
        List<ClassVo> t = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            ClassVo v = new ClassVo();
            v.setName("test" + i);
            v.setClasses(2014);
            v.setEnterTime(new Date());
            t.add(v);
        }
        FileOutputStream fielOut = new FileOutputStream("D:\\tmp\\Out123.xlsx");
//        WriteInterface write = ExcelUtils.newGeneraWrite(t, ClassVo.class);
//        write.write(fielOut);
        ExcelUtils.newGeneraWrite(t, ClassVo.class).write(fielOut);
    }
}
